FROM circleci/android:api-28-alpha

RUN sudo mkdir -p /opt/workspace && sudo chmod 777 /opt/workspace
WORKDIR  /opt/workspace
RUN cd /opt/workspace