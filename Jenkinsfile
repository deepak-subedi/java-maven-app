#!/usr/bin/env groovy

pipeline {
    agent any

    tools {
        maven "maven-3.8"
    }

    environment {
        IMAGE_NAME = "deepaksubedi311/demo-app"
        IMAGE_VERSION = "1.2.0"
    }

    stages {
        stage("build Jar") {
            steps {
                script {
                    echo "Building the application..."
                    sh "mvn package"
                }
            }
        }

        stage("build image") {
            steps {
                script {
                    echo "Building the docker image..."
                    withCredentials([usernamePassword(credentialsId: "docker-hub-credentials", usernameVariable: "USERNAME", passwordVariable: "PASSWORD")]) {
                        sh "docker build -t $IMAGE_NAME:$IMAGE_VERSION ."
                        sh "echo $PASSWORD | docker login -u $USERNAME --password-stdin"
                        sh "docker push $IMAGE_NAME:$IMAGE_VERSION"
                    }
                }
            }
        }

        stage('deploy') {
            steps {
                script {
                    echo "Deploying the application..."
                }
            }
        }
    }
}