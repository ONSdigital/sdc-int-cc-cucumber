FROM adoptopenjdk/maven-openjdk11:latest

RUN apt-get update && apt-get install -y software-properties-common
RUN apt-get update
RUN add-apt-repository ppa:mozillateam/ppa
RUN apt-get update
RUN apt-get -yq clean
RUN apt-get --yes --force-yes install firefox-esr

RUN groupadd -g 985 ccsvc && \
    useradd -m -r -u 985 -g ccsvc ccsvc

ENV M2_HOME=/home/ccsvc/.m2
RUN mkdir -p /home/ccsvc/.m2/repository
RUN ln -s /usr/bin/firefox-esr /usr/bin/firefox
COPY . /home/ccsvc/cc-cucumber
RUN chown -R ccsvc:ccsvc /home/ccsvc
COPY .maven.settings.xml /home/ccsvc/.m2/settings.xml
WORKDIR /home/ccsvc/cc-cucumber

ENV CLOUDSDK_INSTALL_DIR /usr/local/gcloud/
RUN curl -sSL https://sdk.cloud.google.com | bash
ENV PATH $PATH:/usr/local/gcloud/google-cloud-sdk/bin

USER ccsvc
ENTRYPOINT ["/bin/bash"]

