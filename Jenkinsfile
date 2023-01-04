def gv 

pipeline {
    agent any

    tools {
        maven "maven-3.8"
    }

    environment {
        IMAGE_NAME = "deepaksubedi311/demo-app"
    }

    stages {
        stage("init") {
            steps {
                script {
                    gv = load "scripts.groovy"
                }
            }
        }

        stage("increase version") {
            steps {
                script {
                    gv.incrementVersion()
                }
            }
        }

        stage("build Jar") {
            steps {
                script {
                    gv.buildJar()
                }
            }
        }

        stage("build image") {
            steps {
                script {
                    gv.buildImage()
                }
            }
        }

        stage('deploy') {
            steps {
                script {
                    gv.deployApp()
                }
            }
        }
    }
}