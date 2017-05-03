#!groovy

def MAJOR_VERSION = "1.0"

node {

//    properties(
//            [[$class: 'ParametersDefinitionProperty', parameterDefinitions:
//                    [[$class: 'StringParameterDefinition', defaultValue: "1.0", description:
//                            "The major release of this project", name: 'MAJOR_VERSION'
//                     ]]
//             ]])

    def java = tool 'java 8'
    def maven = tool 'maven 3.3.9'

    def RELEASE_NUMBER = "$MAJOR_VERSION.$BUILD_NUMBER"
    def RELEASE_BRANCH = "$JOB_NAME-$RELEASE_NUMBER"

    stage('Checkout') {
        // checkout master
        git "https://github.com/macInfinity/calendar-monster.git"
    }

    stage('Build') {
        // the complete build and push to repository
        withEnv(["JAVA_HOME=$java",
                 "PATH+MAVEN=$maven/bin:${env.JAVA_HOME}/bin"]) {


            // create release branch
//            sh "git checkout -b $RELEASE_BRANCH"

            try {
                // update version number
                mvn "org.codehaus.mojo:versions-maven-plugin:2.3:set -DnewVersion=$RELEASE_NUMBER"

                // build artifact, always look for updates
                // this could be deploy instead of install OR we can push later
                mvn "clean install -U"

            } catch (error) {
                echo "caught error: " + error
            } finally {
                junit '**/target/*.xml'

            }

    }

    stage('Commit and Push') {
        sh "git commit -a -m \"new release candidate\" "
        sh "git push origin $RELEASE_BRANCH"
    }

}

