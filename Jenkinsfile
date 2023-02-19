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

        stage("deploy app to Linode server") {
            steps {
                script {
                    echo "deploying docker image to Linode server"
                    // def dockerCmd = "docker run -p 8080:8080 -d $IMAGE_NAME:$IMAGE_VERSION"
                    def shellCmd = "bash ./server-cmds.sh ${IMAGE_NAME} ${IMAGE_VERSION}"
                    def linodeInstance = "root@172.105.218.125"
                    sshagent(['linode-credential']) {
                        sh "scp server-cmds.sh ${linodeInstance}:/root"
                        sh "scp docker-compose.yaml ${linodeInstance}:/root"
                        sh "ssh -o StrictHostKeyChecking=no ${linodeInstance} ${shellCmd}"
                    }
                }
            }
        }
    }
}