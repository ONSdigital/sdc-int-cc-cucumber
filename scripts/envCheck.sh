#!/bin/bash
#
# This script checks to see if the CC Cucumber prerequisites, needed for
# local execution are running.
#
# If any services are not responding then an error message is printed and 
# the script exits with a non zero code.
#

mock_services_port="8162"
pubsub_emulator_port="9808"
redis_port="6379"
cc_service_port="8171"
ccui_port="5000"

error_found=0

RED='\033[0;31m'
NO_COLOUR='\033[0m'


function verify_service_running {
  service_name=$1
  url=$2
  expected_status=$3

  status=$(curl -s -o /dev/null -w '%{http_code}' $url)

  if [ $status != $expected_status ]
  then 
    echo "$service_name: NOT RUNNING  Response=$status  Expected:$expected_status"
    error_found=1
  else 
    echo "$service_name: ok"
  fi
}


function verify_redis_running {
  redis_host=$1
  redis_port=$2

  result=$(echo PING | nc $redis_host $redis_port | tr -d '\r\n')
  
  if [ $result != "+PONG" ]
  then 
    echo "Redis          : NOT RUNNING  Response=$result  Expected:+PONG"
    error_found=1
  else 
    echo "Redis          : ok"
  fi
}

function verify_postgres {
  docker inspect postgres | grep '"Status": "running"' >/dev/null 2>&1
  if [[ "$?" = "0" ]]
  then
    echo "Postgres       : ok"
  else
    echo "Postgres       : NOT RUNNING"
    error_found=1
  fi
}


# Check to see that the required services appear to be running
verify_service_running "Mock Services  " "http://localhost:$mock_services_port/info" "200" 
verify_service_running "PubSub emulator" "http://localhost:$pubsub_emulator_port" "200"
verify_postgres        "Posgres        "
verify_redis_running "localhost" "$redis_port"
verify_service_running "CC Service     " "http://localhost:$cc_service_port/ccsvc/info" "200"
verify_service_running "CC UI          " "http://localhost:$ccui_port/info" "200"


# Complain if any services not running
if [ $error_found != 0 ]
then
  echo -e "${RED}ERROR: At least one service is not running${NO_COLOUR}"
  exit 1
fi

#EOF
