#!groovy

// Initial version of this file came from: https://www.youtube.com/watch?v=ORNDwYXa4nQ
//
def MAJOR_VERSION="1.0"
node {

    def java = tool 'java 8'
    def maven = tool 'maven 3.3.9'

    def RELEASE_NUMBER="$MAJOR_VERSION.$BUILD_NUMBER"
    def RELEASE_BRANCH="$JOB_NAME-$RELEASE_NUMBER"

    stage('Checkout') {
        // checkout master
        git "https://github.com/macInfinity/calendar-monster.git"
    }

    stage('Build') {
        withEnv(["JAVA_HOME=$java",
                 "PATH+MAVEN=$maven/bin:${env.JAVA_HOME}/bin"]) {

            try {
                // update version number
                mvn  "org.codehaus.mojo:versions-maven-plugin:2.3:set -DnewVersion=$RELEASE_NUMBER"

                // build artifact, always look for updates
                // this could be deploy instead of install OR we can push later
                mvn "clean install -U"

            } catch( error) {
                echo "caught error: " + error
            } finally {
                junit '**/target/*.xml'

            }
        }
    }

    stage('Tag and Push') {
        git credentialsId: 'macInfinity-github',
                url: 'git@github.com:macInfinity/calendar-monster.git',
                script: "git tag -a $RELEASE_BRANCH -m \"new release candidate\" "

        git credentialsId: 'macInfinity-github',
                url: 'git@github.com:macInfinity/calendar-monster.git',
                script: "git push origin $RELEASE_BRANCH"

//        withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: 'macInfinity',
//                          usernameVariable: 'macInfinity', passwordVariable: 'xxx']]) {
//
//            sh "git tag -a $RELEASE_BRANCH -m \"new release candidate\" "
//            sh "git push origin $RELEASE_BRANCH"
        }
    }

}

// the main build needs the following:
// MAJOR_VERSION value, this used to be set in Jenkins, say 2.0 or 3.1
// BUILD_NUMBER this value is the build number from Jenkins, if this is build 234, then this
//                value is 234
// RELEASE_NUMBER $MAJOR_VERSION.$BUILD_NUMBER
// RELEASE_BRANCH this is the branch name we'll create in GIT and is: $project-$RELEASE_NUMBER