pipeline {
    agent any

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
                echo "Build JAR"
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