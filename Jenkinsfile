pipeline {

  options {
      timeout(time: 1, unit: 'HOURS')
  }

  agent {
    docker {
      image 'mxml/jdk-scp'
      alwaysPull true
    }
  }

  environment {
    GRADLE_PUBLISH_KEY     = credentials('org-padler-publish-key')
    GRADLE_PUBLISH_SECRET  = credentials('org-padler-publish-secret')
  }

  stages {

    stage('build') {
      steps {
        sh 'java -version'
        sh './gradlew clean assemble'
      }
    }
    stage('Test') {
      steps {
        sh './gradlew check'
      }
    }

    stage('Archive .jar'){
      steps {
        archiveArtifacts  'build/libs/**/*.jar'
      }
    }

    stage('release'){
      when{
        branch 'master'
      }
      steps {
        sh './gradlew publishPlugins'
      }
    }
  }

  post {
    success {
      echo "Build successful"
    }
    always {
      junit 'build/test-results/**/*.xml'
      cleanWs()
    }
  }
}
