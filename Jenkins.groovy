pipeline {
    agent any
    
    stages {
        stage('Clearing Workspace') {
            steps {
                cleanWs()
            }
        }
        
        stage('Cloning the Repository') {
            steps {
                sh 'git clone "https://github.com/Abhibhagat1407/heroes-angular.git"'
            }
        }
        
        stage('Changing to Repository Directory') {
            steps {
                dir('heroes-angular') {
                    // Run commands inside the 'heroes-angular' directory
                    sh 'npm install'
                    
                    // Copy Nginx configuration
                    sh 'sudo cp heroes-angular.conf /etc/nginx/sites-available/'
                    
                    // Enable site
                    sh 'sudo rm -f /etc/nginx/sites-enabled/heroes-angular.conf'
                    sh 'sudo ln -s /etc/nginx/sites-available/heroes-angular.conf /etc/nginx/sites-enabled/'
                    
                    // Reload Nginx
                    sh 'sudo systemctl reload nginx'
                    
                    // Start application
                    sh 'npm run quick'
                    sh 'npm start'
                }
            }
        }
        
      
    }
    
    post {
        failure {
            echo 'Pipeline failed'
            
        }
    }
}
