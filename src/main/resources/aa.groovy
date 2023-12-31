pipeline {
    agent  {
        node {
            // 定义执行的node, 匹配对应的node tag－－－－－－－－－－－－－如何指定多个标签，唯一指定一个node？？？
            label 'java'
        }
    }
    // 配置环境变量，适用于Pipeline中的所有步骤
    environment {
        BRANCH = "${BRANCH}"
        M2_HOME = '/usr/lib/maven/apache-maven-3.6.0'
        // 修改mvn的path，这样即使mvn可执行文件不在/usr/bin/mvn下，也没有关系
        PATH = "${env.PATH}:${env.M2_HOME}/bin"
    }
    // 参数定义，该定义会可视化到jenkins配置上
    parameters {
        string(name: 'PERSON', defaultValue: 'Mr Jenkins', description: 'Who should I say hello to?')
    }
    stages {
        // 输入提示测试
        stage('Init') {
            // 条件配置，当环境变量BRANCH值为develop时执行
            when {
                environment name: 'BRANCH', value: 'develop'
            }
            input {
                //　提示消息
                message "Should we continue?"
                // 确认执行提示消息
                ok "Yes, we should."
                // 允许提交此input选项的用户或外部组名列表，用逗号分隔。默认允许任何用户
                submitter "alice,bob"
                // 参数
                parameters {
                    string(name: 'PERSON1', defaultValue: 'Mr Jenkins', description: 'Who should I say hello to?')
                }
            }
        }
        // 并行测试
        stage('Parallel Stage') {
            // 条件配置，当环境变量BRANCH值为develop时执行
            when {
                environment name: 'BRANCH', value: 'develop'
            }
            // 配置当其中一个进程失败时，你可以强制所有的 parallel 阶段都被终止（只会abort那些时间长的）
            failFast true
            parallel {
                stage('Branch A') {
                    steps {
                        echo "On Branch A"
                    }
                }
                stage('Branch B') {
                    steps {
                        echo "On Branch B"
                    }
                }
            }
        }
        //---------------------------------node 本机执行－－－－－－－－－－－－－－－
        stage('Build') {
            // 在stage中定义的environment指令只将给定的环境变量应用于该stage中的步骤
            environment {
                M2_HOME = '/usr/lib/maven/apache-maven-3.6.0'
            }
            // 可选配置，此处指定执行超时时间1小时，之后Jenkins将中止Pipeline运行
            options {
                timeout(time: 1, unit: 'HOURS')
            }
            // 一个stage内只能有一个steps
            steps {
                echo 'Hello World Build'
                // 使用参数
                echo "Hello ${params.PERSON}"
                // 使用environment中定义的环境变量
                echo "${env.M2_HOME}"
                // sh后面接要执行的命令
                sh 'mvn -version'
                sh 'mvn clean install -Dmaven.test.skip=true'
                echo "${BRANCH}"
            }
        }
        stage('Deploy') {
            steps {
                echo 'Hello World Deploy'
                sh 'chmod 777 deploy.sh'
                // 执行脚本，脚本保存在git代码管理项目根目录中，具体目录可以对于修改命令
                sh './deploy.sh'
            }
        }
        //---------------------------------node 本机执行－－－－－－－－－－－－－－－
    }
}