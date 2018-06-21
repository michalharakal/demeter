FROM circleci/android:api-28-alpha

RUN mkdir -p /opt/workspace && chmod 777 /opt/workspace
WORKDIR  /opt/workspace