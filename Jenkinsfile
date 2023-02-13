pipeline {
    agent any

    environment {
        IMAGE_NAME = "deepaksubedi311/demo-app"
    }

    stages {
        stage("init") {
            steps {
                script {
                    sh "initiate project"
                }
            }
        }

        stage("build JAR") {
            steps {
                script {
                    sh "build JAR file"
                }
            }
        }

        stage("build docker") {
            steps {
                script {
                    sh "build docker image"
                }
            }
        }

        stage("deploy app") {
            steps {
                script {
                    sh "deploy an app $IMAGE_NAME"
                }
            }
        }
    }
}