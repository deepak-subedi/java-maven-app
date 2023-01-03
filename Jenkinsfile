#!/usr/bin/env groovy
def gv 

pipeline {
    agent any

    tools {
        maven "maven-3.8"
    }

    environment {
        IMAGE_NAME = "deepaksubedi311/demo-app"
        IMAGE_VERSION = "1.3.0"
    }

    stages {
        stage("init") {
            steps {
                script {
                    gv = load "scripts.groovy"
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