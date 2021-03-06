#!/bin/bash
#
# Run CC cucumber tests , screening out distracting firefox messages.
#

which unbuffer >/dev/null 2>&1
if [[ "$?" != "0" ]]
then
	echo "Please install 'unbuffer'. This can be done with 'brew install expect'"
	exit 2
fi

# Delete any previously dumped page
if [ -d "/tmp" ]
then
    rm -f /tmp/cc-cuc.*.html
    rm -f /tmp/cc-cuc.*.html
fi

echo "Running cucumber tests ..."
(
	unbuffer mvn clean install $* |
		grep --line-buffered -v 'RenderCompositorSWGL failed mapping default framebuffer' |
		grep --line-buffered -v 'WARNING: FileDescriptorSet destroyed with unconsumed descriptors' |
		grep --line-buffered -v 'No settings file exists, new profile' |
		grep --line-buffered -v 'You are running in headless mode.' |
		grep --line-buffered -v 'Failed to check for Maintenance Service Registry Key' |
		grep --line-buffered -v 'console.error: BackgroundUpdate:' |
		grep --line-buffered -v 'Channel error: cannot send/recv' |
		grep --line-buffered -v 'AbortError: A request was aborted, for example through a call to IDBTransaction.abort' |
		grep --line-buffered -v 'Channel closing: too late to send/recv, messages will be lost' |
		grep --line-buffered -v 'NS_ERROR_FAILURE' |
		grep --line-buffered -v 'Marionette	INFO' |
		sed '/^$/N;/^\n$/D'
)

# EOF
