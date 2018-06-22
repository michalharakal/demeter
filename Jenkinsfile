pipeline {
  agent any
  environment {
        DEMETER_UI_APP_HOCKEYAPP_API_TOKEN = credentials('demeter_ui-app-hockeyapp-api-token')
        HockeyAppID = "7cacbee9945c4c9aa1ac59889858c44c"
  }
 
  stages {
    stage('Docker Build') {
      agent any
      steps {
        sh 'docker build -t demeter .'
      }
    }  
    stage('Gradle test') {
      agent {
        docker {
          image 'demeter'
        }
      }
      steps {
        sh './gradlew --stacktrace --info clean test'
      }
    }

    stage('Gradle build') {
      agent {
        docker {
          image 'demeter'
        }
      }
      steps {
        sh './gradlew --stacktrace --info clean assembleDebug'
        archiveArtifacts 'mobile-ui/build/outputs/apk/debug/mobile-ui-debug.apk'    
      }
    }

    // https://github.com/gini/gini-vision-lib-android/blob/master/Jenkinsfile
    stage('Upload Apps to Hockeyapp') {
            steps {
                	// sh 'curl -X POST --data-urlencode \'payload=${payload}\' ${HockeyAppURL}"   
				sh """
				    curl  \
				    -F 'status=2' \
				    -F 'notify=0' \
				    -F 'strategy=replace' \
				    -F 'notes_type=0'\
				    -F 'tags=development' \
				    -F 'ipa=/mobile-ui/build/outputs/apk/debug/mobile-ui-debug.apk' \
				    -H 'X-HockeyAppToken:${DEMETER_UI_APP_HOCKEYAPP_API_TOKEN}' \
				    https://upload.hockeyapp.net/api/2/apps/${HockeyAppID}/app_versions/upload"""
            }
    }
  }
}
