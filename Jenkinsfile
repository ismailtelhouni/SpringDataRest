pipeline {
    agent any

    stages {
        stage('Clone Repositories') {
            steps {
                // Cloner le dépôt front-end
                dir('frontend') {
                    git url: 'https://github.com/ismailtelhouni/VoitureAppFrontend.git', branch: 'main'
                }
                // Cloner le dépôt back-end
                dir('backend') {
                    git url: 'https://github.com/ismailtelhouni/SpringDataRest.git', branch: 'main'
                }
            }
        }
        stage('Check Files') {
            steps {
                dir('backend'){
                    script {
                        sh 'ls -la SpringDataRest'
                    }
                }
            }
        }
        stage ( 'Gitleaks Scan') {
            steps {
                dir('backend'){
                    script {
                        echo " Starting Gitleaks scan "
                        // Run the Gitleaks scan and allow the pipeline to continue even if errors occur
                        def result = sh(
                            script: """
                            docker pull zricethezav/gitleaks:latest
                            docker run --rm -v ${WORKSPACE}:/path \
                            zricethezav/gitleaks:latest \
                            detect \
                            --source /path \
                            --report-path /path/gitleaks-report.json \
                            --report-format json
                            """,
                            returnStatus: true // Captures the exit status instead of failing
                        )

                        if (result != 0) {
                            echo "Gitleaks found issues or encountered an error, but continuing with the pipeline."
                        } else {
                            echo "Gitleaks scan completed successfully with no issues."
                        }

                        // Display the contents of the report in the console
                        sh "cat ${WORKSPACE}/gitleaks-report.json || echo 'No report file generated'"

                    }
                }
            }
        }

        stage('OWASP Dependency-Check Vulnerabilities') {
            steps {
                dependencyCheck additionalArguments: '''
                    -o './'
                    -s './'
                    -f 'ALL'
                    --prettyPrint
                ''', odcInstallation: 'OWASP Dependency-Check Vulnerabilities'

                dependencyCheckPublisher pattern: 'dependency-check-report.xml'
            }
        }

        stage('Build Frontend Application') {
            steps {
                dir('frontend') {
                    // Construire l'application front-end (par exemple avec npm)
                    nodejs('NodeJs') {
                        sh 'npm install'
                        sh 'npm run build'
                    }

                }
            }
        }
        stage('Build Frontend Docker Image') {
            steps {
                dir('frontend') {
                    sh 'docker build -t voiture-app-frontend .'
                }
            }
        }
        stage('Build Backend Docker Image') {
            steps {
                dir('backend') {
                    sh 'mvn clean package -Pbuild-image'
                }
            }
        }
        stage('Run Docker Compose') {
            steps {
                // Lancer Docker Compose
                sh 'docker-compose up -d'
            }
        }
        /* stage('SonarQube Analysis') {
            steps {
                script {
                    def scannerHome = tool 'SonarScanner';
                    withSonarQubeEnv('SonarQube') {
                        sh """
                            ${scannerHome}/bin/sonar-scanner \
                            -Dsonar.projectKey=Angular_2 \
                            -Dsonar.host.url=http://localhost:9000 \
                            -Dsonar.login=sqp_7992fc47e7e98fc3277071f9941f43af0b9d0557 \
                            -Dsonar.sources=src \
                            -Dsonar.exclusions="**//* node_modules *//**"
                        """
                    }
                }
            }
        }
        stage('Quality Gate') {
            steps {
                timeout(time: 5, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        } */
    }
    post {
        always {
            echo "Analyse terminée, vérifiez SonarQube pour les résultats."
        }

        failure {
            script {
                emailext(
                    subject: "Pipeline Failed: ${env.JOB_NAME} ${env.BUILD_NUMBER}",
                    body: """<p>Bonjour,</p>
                             <p>Le pipeline <strong>${env.JOB_NAME}</strong> a échoué à l'étape de Quality Gate lors de l'exécution de la build numéro <strong>${env.BUILD_NUMBER}</strong>.</p>
                             <p>Statut de la Quality Gate: <strong style="color: red;">${currentBuild.result}</strong></p>
                             <p>Vérifiez les détails de la build ici : <a href="${env.BUILD_URL}">${env.BUILD_URL}</a></p>
                             <p>Cordialement,</p>
                             <p>Votre serveur Jenkins</p>""",
                    to: 'ismailtelhouni123@gmail.com', // Remplacez par les adresses souhaitées
                    from:"chakra.hs.business@gmail.com",
                    replyTo:"chakra.hs.business@gmail.com",
                    mimeType: 'text/html'
                )
            }
        }

        success {
            script {
                emailext(
                    subject: "Pipeline Succeeded: ${env.JOB_NAME} ${env.BUILD_NUMBER}",
                    body: """<p>Bonjour,</p>
                             <p>Le pipeline <strong>${env.JOB_NAME}</strong> s'est terminé avec succès à l'étape de Quality Gate lors de l'exécution de la build numéro <strong>${env.BUILD_NUMBER}</strong>.</p>
                             <p>Statut de la Quality Gate: <strong style="color: green;">${currentBuild.result}</strong></p>
                             <p>Vérifiez les détails de la build ici : <a href="${env.BUILD_URL}">${env.BUILD_URL}</a></p>
                             <p>Cordialement,</p>
                             <p>Votre serveur Jenkins</p>""",
                    to: 'ismailtelhouni123@gmail.com', // Remplacez par les adresses souhaitées
                    from:"chakra.hs.business@gmail.com",
                    replyTo:"chakra.hs.business@gmail.com",
                    mimeType: 'text/html'
                )
            }
        }
    }
}