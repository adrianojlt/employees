#!/bin/zsh
IMAGE_NAME=${1:-virtuoso-employees}
TAG=${2:-latest}
REGISTRY=${3:-teixeiradas.ddns.net:5005}

# to be able to push to a remote registry, you need to login first
# docker login $REGISTRY

echo "Building the Maven project..."
./gradlew clean build
if [ $? -ne 0 ]; then
    echo "Gradle build failed. Exiting..."
    exit 1
fi

echo "Building the Docker image for linux/amd64..."
docker buildx build --platform linux/amd64 -t $IMAGE_NAME .

if [ $? -ne 0 ]; then
    echo "Docker buildx build failed. Exiting..."
    exit 1
fi

echo "Tagging the Docker image..."
sudo docker tag $IMAGE_NAME $REGISTRY/$IMAGE_NAME:$TAG
if [ $? -ne 0 ]; then
    echo "Docker tagging failed. Exiting..."
    exit 1
fi

echo "Pushing the Docker image..."
sudo docker push $REGISTRY/$IMAGE_NAME:$TAG
if [ $? -ne 0 ]; then
    echo "Docker push failed. Exiting..."
    exit 1
fi

echo "Deployment completed successfully!"