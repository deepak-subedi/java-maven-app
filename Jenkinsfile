pipeline {
    agent any

    environment {
        IMAGE_NAME = "deepaksubedi311/demo-app"
    }

    stages {
        stage("init") {
            script {
                sh "Initiate github"
            }
        }

        stage("build JAR") {
            script {
                sh "build JAR file"
            }
        }

        stage("build docker") {
            script {
                sh "build docker image"
            }
        }

        stage("deploy app") {
            script {
                sh "deploy an app $IMAGE_NAME"
            }
        }
    }
}