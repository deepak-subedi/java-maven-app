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

        stage("provision server") {
            environment {
                AWS_ACCESS_KEY_ID = credentials("aws_access_key_id")
                AWS_SECRET_ACCESS_KEY = credentials("aws_secret_access_key")
                TF_VAR_env_prefix = "test"
            }

            steps {
                script {
                    dir("terraform") {
                        sh "terraform init"
                        sh "terraform apply --auto-approve"
                        EC2_PUBLIC_IP = sh(
                            script: "terraform output ec2_public_ip",
                            returnStdout: true
                        ).trim()
                    }
                }
            }
        }

        stage("deploy app to ec2 server") {
            environment {
                DOCKER_CREDS = credentials("docker-hub-credential") 
            }

            steps {
                script {
                    echo "waiting for EC2 server to initialize"
                    sleep(time: 90, unit: "SECONDS")

                    echo "deploying docker image to ec2 server"
                    echo "${EC2_PUBLIC_IP}"

                    // def dockerCmd = "docker run -p 8080:8080 -d $IMAGE_NAME:$IMAGE_VERSION"
                    def shellCmd = "bash ./server-cmds.sh ${IMAGE_NAME} ${IMAGE_VERSION} ${DOCKER_CREDS_USR} ${DOCKER_CREDS_PSW}"
                    def ec2Instance = "ec2-user@${EC2_PUBLIC_IP}"

                    sshagent(['terraform-ec2-ssh-key']) {
                        sh "scp -o StrictHostKeyChecking=no server-cmds.sh ${ec2Instance}:/home/ec2-user"
                        sh "scp -o StrictHostKeyChecking=no docker-compose.yaml ${ec2Instance}:/home/ec2-user"
                        sh "ssh -o StrictHostKeyChecking=no ${ec2Instance} ${shellCmd}"
                    }
                }
            }
        }
    }

    post {
        always {
            echo "Deleting local images..."
            sh "docker image purne -af"
        }
    }
}