#!groovy

// Initial version of this file came from: https://www.youtube.com/watch?v=ORNDwYXa4nQ
//
node {
    properties([
            parameters([
                    string(defaultValue: '1.0', description:
                            'The major release of this project', name: 'MAJOR_RELEASE'
                    )
            ])
    ])

    // checkout master
    git "https://github.com/macInfinity/calendar-monster.git"

    def java = tool 'java 8'
    def maven = tool 'maven 3.3.9'

    // the complete build and push to repository
    withEnv(["JAVA_HOME=$java",
            "PATH+MAVEN=$maven/bin:${env.JAVA_HOME}/bin",
            "RELEASE_NUMBER=$MAJOR_RELEASE.$BUILD_NUMBER",
            "RELEASE_BRANCH=$JOB_NAME-$RELEASE_NUMBER"]) {

        // create release branch
        sh "git checkout -b $RELEASE_BRANCH"

        // update version number
        sh "mvn org.codehaus.mojo:versions-maven-plugin:2.3:set -DnewVersion=$RELEASE_NUMBER"

        // build artifact
        sh "mvn clean install"

        sh "git commit -a -m \"new release candidate\" "
        sh "git push origin $RELEASE_BRANCH"

    }
}

// the main build needs the following:
// MAJOR_RELEASE value, this used to be set in Jenkins, say 2.0 or 3.1
// BUILD_NUMBER this value is the build number from Jenkins, if this is build 234, then this
//                value is 234
// RELEASE_NUMBER $MAJOR_RELEASE.$BUILD_NUMBER
// RELEASE_BRANCH this is the branch name we'll create in GIT and is: $project-$RELEASE_NUMBER