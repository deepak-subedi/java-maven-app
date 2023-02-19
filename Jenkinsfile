pipeline {
    agent any

    tools {
        maven "maven"
    }

    environment {
        IMAGE_NAME = "deepaksubedi311/demo-app"
    }

    stages {
        // stage("GIT Checkout") {
        //     steps {
        //         git branch: 'main', 
        //         credentialsId: 'github-credential', 
        //         url: 'https://github.com/deepak-subedi/java-maven-app.git'
        //     }
        // }

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

        stage("build & push docker images") {
            steps {
                echo "Building the docker image..."
                withCredentials([usernamePassword(credentialsId: "docker-hub-credential", usernameVariable: "USERNAME", passwordVariable: "PASSWORD")]) {
                    sh "docker build -t $IMAGE_NAME:$IMAGE_VERSION ."
                    sh "echo $PASSWORD | docker login -u $USERNAME --password-stdin"
                    sh "docker push $IMAGE_NAME:$IMAGE_VERSION"
                }
            }
        }

        stage("deploy app") {
            steps {
                script {
                    def dockerCmd = "docker run -p 3080:3080 -d $IMAGE_NAME:$IMAGE_VERSION"
                    sshagent(['linode-credential']) {
                        sh "ssh -o StrictHostKeyChecking=no root@172.105.218.125 ${dockerCmd}"
                    }
                }
            }
        }
    }
}