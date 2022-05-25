#!/usr/bin/env bash

#-VARIAVEIS INFO-----------------------------------------------------#

NOME_PROGRAMA="$(basename $0 | cut -d. -f1)"
VERSAO="1.0"
AUTOR="Ingresse"
CONTATO="https://github.com/CarlosDominciano/PI-Grupo-5"
DESCRICAO="Script para executar o .jar do projeto do cluster"
varEXE=$1 # Se não tiver parametros ela executa normal


#-VARIAVEIS PARAMETRO----------------------------------------------------#

varINFO="
Nome do Programa: $NOME_PROGRAMA
Autor: $AUTOR
Versão: $VERSAO
Descrição do Programa: $DESCRICAO
"
varHELP="
Instruções para Ajuda:
	-h ou --help: Abre a ajuda de comandos do usuário;
	-i ou --info: Informações sobre o programa;
"

#-TESTES--------------------------------------------------------------------------#



#-LOOP PARA RODAR MAIS PARAMETROS---------------------------------------------------#

while test -n "$1"; do

	case $1 in

		-i |  --info)  	echo "$varINFO" 											;;		
		-h |  --help)  	echo "$varHELP"												;;
		-d | --debug)	bash -x $0													;;
		 *) 	echo "\nComando inválido. Digite -h ou --help para ajuda\n"	;;

	esac
	shift

done
#-FUNÇÕES--------------------------------------------------------------------------#
instalar_pacotes(){
	echo "\n\n=================================================="
	echo "Instalando e verificando todos os pacotes..."
	sudo apt-get update && sudo apt-get upgrade -y
	echo "\n\n=================================================="
	echo "Verificando docker..."
	echo "==================================================\n\n"
	sudo apt install docker.io -y
}
criar_ingresse100(){
	echo "\n\n=================================================="
	echo "Criando usuário ingresse100.."
	echo "==================================================\n\n"
	useradd -m -U ingresse100
	usermod -aG sudo ingresse100
}
clonar_github(){

	echo "\n\n=================================================="
	echo "Buscando .jar no github ingresse"
	echo "==================================================\n\n"
	wget -O ingresseCLI.jar https://github.com/diegozn/docker/blob/main/ingresse_app/ingresse_software.jar?raw=true
	chmod 777 ingresseCLI.jar
	echo "\n\n=================================================="
	echo "Criando uma pasta para o projeto..."
	echo "==================================================\n\n"
	mkdir totem && mv ./ingresseCLI.jar totem/ingresseCLI.jar && cd totem

}
instalar_docker(){
	sudo systemctl start docker
	sudo systemctl enable  docker
	echo "\n\n=================================================="
	echo "Criando uma network..."
	echo "==================================================\n\n"
	sudo docker network create totem-net
	echo "\n\n=================================================="
	echo "Rodando mysql no Docker"
	echo "==================================================\n\n"
	
    sudo docker build -t ingresse-image ./mysql/.
	sudo docker run -d --name mysql-totem -p 3306:3306 --net=totem-net ingresse-image
	cd ..
	echo "\n\n=================================================="
	echo "Rodando java no Docker"
	echo "==================================================\n\n"
	
	sudo docker build -t java-image ./java/.
	sudo docker run -it --name java-totem --link mysql-totem --net=totem-net java-image                                                           

}

main(){
	criar_ingresse100
	clear
	instalar_pacotes
	clear
	clonar_github
	clear
	baixar_scripts
	clear
	instalar_docker
}

baixar_scripts(){

	mkdir mysql
	mkdir java
	wget -O Dockerfile https://raw.githubusercontent.com/diegozn/docker/main/ingresse_app/Dockerfile
	mv ./Dockerfile ./java/Dockerfile
	wget -O Dockerfile https://raw.githubusercontent.com/diegozn/docker/main/ingresse_banco/Dockerfile
	mv ./Dockerfile ./mysql/Dockerfile
	wget -O sql.sql https://raw.githubusercontent.com/diegozn/docker/main/ingresse_banco/sql.sql
	mv ./sql.sql ./mysql/sql.sql
	mv ./ingresseCLI.jar ./java/ingresseCLI.jar
}

if [ -z "$varEXE" ]; then
	main
fi





