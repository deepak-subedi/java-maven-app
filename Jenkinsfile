pipeline {
    agent any

    environment {
        IMAGE_NAME = "deepaksubedi311/demo-app"
    }

    stages {
        stage("init") {
            steps {
                sh "initiate project"
            }
        }

        stage("build JAR") {
            steps {
                sh "build JAR file"
            }
        }

        stage("build docker") {
            steps {
                sh "build docker image"
            }
        }

        stage("deploy app") {
            steps {
                sh "deploy an app $IMAGE_NAME"
            }
        }
    }
}