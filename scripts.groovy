def incrementVersion() {
    echo "Increasing the app version..."
    sh 'mvn build-helper:parse-version version:set \
        -DnewVersion=\\\${parsedVersion.majorVersion}.\\\${parsedVersion.minorVersion}.\\\${parsedVersion.nextIncrementalVersion} \
        versions:commit'
    def matcher = readFile('pom.xml') =~ '<version>(.+)</version>'
    def version = matcher[0][1]
    env.IMAGE_VERSION = "$version=$BUILD_NUMBER"
}

def buildJar() {
    echo "Building the application..."
    sh "mvn clean package"
}

def buildImage() {
    echo "Building the docker image..."
    withCredentials([usernamePassword(credentialsId: "docker-hub-credentials", usernameVariable: "USERNAME", passwordVariable: "PASSWORD")]) {
        sh "docker build -t $IMAGE_NAME:$IMAGE_VERSION ."
        sh "echo $PASSWORD | docker login -u $USERNAME --password-stdin"
        sh "docker push $IMAGE_NAME:$IMAGE_VERSION"
    }
}

def deployApp() {
    echo "Deploying image..."
}

return this