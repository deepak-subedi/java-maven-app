pipeline {
    agent any

    environment {
        IMAGE_NAME = "deepaksubedi311/demo-app"
    }

    stages {
        stage("init") {
            sh "Initiate github"
        }

        stage("build JAR") {
            sh "build JAR file"
        }

        stage("build docker") {
            sh "build docker image"
        }

        stage("deploy app") {
            sh "deploy an app $IMAGE_NAME"
        }
    }
}