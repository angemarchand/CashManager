FROM mobiledevops/flutter-sdk-image as build

#Update sdkmanager packages
RUN $ANDROID_HOME/cmdline-tools/bin/sdkmanager --update  --sdk_root=${ANDROID_SDK_ROOT} && \
#Flutter need the latest android command line tools
  $ANDROID_HOME/cmdline-tools/bin/sdkmanager --install "cmdline-tools;latest"  --sdk_root=${ANDROID_SDK_ROOT} && \
  # add flutter to safe directories of git
  git config --global --add safe.directory /home/mobiledevops/.flutter-sdk

RUN  flutter doctor --android-licenses

WORKDIR /client

RUN flutter upgrade

COPY pubspec.* .

# Make sure to have specific version of dependency
RUN flutter pub get

COPY . .

#USER root

RUN flutter clean && flutter build apk

# Creation of a second stage to lighten the image
FROM busybox

# the apk is in /var/lib/docker/volumes/cash-manager_apk/_data of docker storage
COPY --from=build /client/build/app/outputs/flutter-apk/app-release.apk /apk/client.apk