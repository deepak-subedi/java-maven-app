pipeline {
    agent any

    tools {
        maven "maven"
    }

    environment {
        IMAGE_NAME = "deepaksubedi311/demo-app"
    }


    stages {
        stage("GIT Checkout") {
            steps {
                git branch: 'main', credentialsId: 'github-credential', url: 'https://github.com/deepak-subedi/java-maven-app.git'
            }
        }

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
                script {
                    withCredentials([usernamePassword(credentialsId: 'github-credential', passwordVariable: 'GIT_PASSWORD', usernameVariable: 'GIT_USERNAME')]) {
                        def encodedPassword = URLEncoder.encode("$GIT_PASSWORD",'UTF-8')
                        sh "git config --global user.email 'jenkins@gmail.com'"
                        sh "git config --global user.name 'jenkins'"

                        sh "git status"
                        sh "git branch"
                        sh "git config --list"

                        sh "git remote set-url origin https://${GIT_USERNAME}:${encodedPassword}@github.com/deepak-subedi/java-maven-app.git"
                        sh "git add ."
                        sh 'git commit -m "ci: version bump"'
                        sh "git push origin HEAD:main"
                    }     
                }
                     
            }
        }
    }
}