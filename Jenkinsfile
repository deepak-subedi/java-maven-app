pipeline {
    agent any

    tools {
        maven "maven"
    }

    environment {
        IMAGE_NAME = "deepaksubedi311/demo-app"
        GITHUB_CRED = credentials('github-credential')
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

        stage("increment version") {
            steps {
                echo "Increasing app version"
                sh '''
                    git config --global user.email 'jenkins@gmail.com'
                    git config --global user.name 'jenkins'

                    git status
                    git branch
                    git config --list

                    git add .
                    git commit -m 'ci: version bump'
                    git push https://$GITHUB_CRED_USR:$GITHUB_CRED_PSW@github.com/$GITHUB_CRED_USR/java-maven-app.git HEAD:main
                '''
            }
        }
    }
}