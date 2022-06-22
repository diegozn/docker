            clear
            echo "Atualizando pacotes do sistema(1/4)"
            sleep 3
            sudo apt update && sudo apt upgrade -y

            sudo apt install zip -y

            curl -s "https://get.sdkman.io" | bash

            source "/home/$USER/.sdkman/bin/sdkman-init.sh"
            clear
            echo "Instalando java(2/4)"
            sleep 3
            sudo apt install openjdk-11-jre -y
            
            sudo apt update && sudo apt upgrade -y
            clear
            echo "Instalando docker(3/4)"
            sleep 3
            sudo apt install docker.io -y

            sudo apt install docker-compose -y

            sudo apt-get install \
            ca-certificates \
            curl \
            gnupg \
            lsb-release -y

            curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /usr/share/keyrings/docker-archive-keyring.gpg

            echo \
            "deb [arch=$(dpkg --print-architecture) signed-by=/usr/share/keyrings/docker-archive-keyring.gpg] https://download.docker.com/linux/ubuntu \
             $(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list >/dev/null

            sudo apt-get update

            sudo apt-get install docker-ce docker-ce-cli containerd.io docker-compose-plugin -y

            sudo apt-get update

            sudo systemctl enable docker

            clear

            echo "Fazendo download do software IngresseSoftware(4/4)"
            sleep 5
            clear
            echo "Instalação efetuada com sucesso!"
            read -p "Pressione Enter para continuar"
            echo "================================================"