#!/bin/bash

build_project() {
  local project_dir=$1
  echo "Building project $project_dir"
  cd $project_dir || exit
  ./gradlew clean build -x test || { echo "Build failed for $project_dir"; exit 1; }
  cd ..
}

docker_build() {
  local image_name=$1
  local project_dir=$2
  echo "Building Docker image $image_name for project $project_dir"
  cd $project_dir || exit
  docker build -t $image_name . || { echo "Docker build failed for $image_name"; exit 1; }
  cd ..
}

echo "Building all projects with Gradle..."

build_project "EurekaServer"
build_project "management-store"
build_project "ManagementSystem"
build_project "PB-AuthUser"
build_project "tcm-gateway"
build_project "TCM-Notification"
build_project "TCM-NotificationConsumer"

echo "All projects built successfully."

echo "Building Docker images..."

docker_build "tcm-eureka-server" "EurekaServer"
docker_build "tcm-store" "management-store"
docker_build "tcm-system" "ManagementSystem"
docker_build "tcm-auth" "PB-AuthUser"
docker_build "tcm-gateway" "tcm-gateway"
docker_build "tcm-notification" "TCM-Notification"
docker_build "tcm-notification-consumer" "TCM-NotificationConsumer"

echo "All Docker images built successfully."


echo "Starting services with Docker Compose..."
cd Docker/ || { echo "Failed to change directory to Docker/"; exit 1; }
docker-compose -f tcm-compose.yaml up --build
