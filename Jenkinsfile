pipeline {
    agent any

    tools {
        maven "maven"
    }

    environment {
        IMAGE_NAME = "deepaksubedi311/demo-app"
    }

    stages {
        stage("increment app version") {
            steps {
                script {
                    echo "Increasing the app version..."
                    sh 'mvn build-helper:parse-version versions:set \
                        -DnewVersion=\\\${parsedVersion.majorVersion}.\\\${parsedVersion.minorVersion}.\\\${parsedVersion.nextIncrementalVersion} \
                        versions:commit'
                    def matcher = readFile('pom.xml') =~ '<version>(.+)</version>'
                    def version = matcher[0][1]
                    env.IMAGE_VERSION = "$version-$BUILD_NUMBER"
                }
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
                echo "Build docker $IMAGE_VERSION"
            }
        }

        stage("deploy app") {
            steps {
                echo "Deploy app"
            }
        }
    }
}