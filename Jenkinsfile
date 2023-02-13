pipeline {
    agent any

    tools {
        maven "maven"
    }

    environment {
        IMAGE_NAME = "deepaksubedi311/demo-app"
    }

    stages {
        stage("init") {
            steps {
                echo "Initialize github"
            }
        }

        stage("build JAR") {
            steps {
                script {
                    echo "Build JAR"
                    sh "mvn clean package"
                }
                
            }
        }

        stage("build docker") {
            steps {
                echo "Build docker"
            }
        }

        stage("deploy app") {
            steps {
                echo "Deploy app"
            }
        }
    }
}