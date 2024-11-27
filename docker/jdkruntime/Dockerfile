FROM registry.access.redhat.com/ubi8/openjdk-21:1.20

ENV LANGUAGE='en_US:en'


USER root
RUN microdnf --setopt=install_weak_deps=0 --setopt=tsflags=nodocs install -y fontconfig \
    && microdnf clean all \
    && rpm -q fontconfig

USER 185

WORKDIR /app
#CMD ["./gradlew", "quarkusDev"]
