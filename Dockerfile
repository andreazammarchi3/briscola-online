FROM alpine:latest
RUN apk update; apk add openjdk17
RUN apk add ffmpeg mesa-gl
COPY ./ /briscola-online/
WORKDIR /briscola-online/
RUN ./gradlew build
ENV GRD_OPTS --console=plain -q
CMD ./gradlew -p server run $GRD_OPTS
