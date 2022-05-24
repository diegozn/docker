
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
	wget -O ingresseCLI.jar https://github.com/CarlosDominciano/PI-Grupo-5/raw/main/Software%20Java/IngresseSofware/target/IngresseSofware-1.0-SNAPSHOT.jar 
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
    sudo docker build -t ingresse-image .
    docker run -d --name ingresse -p 3306:3306 ingresse-image
	sudo docker run -d --name mysql-totem -p 3306:3306 --net=totem-net mysql-image
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
	wget -O Dockerfile https://raw.githubusercontent.com/diegozn/docker/main/Dockerfile
	mv ./Dockerfile ./java/Dockerfile	
	wget -O sql.sql https://raw.githubusercontent.com/diegozn/docker/main/sql.sql
	mv ./sql.sql ./mysql/sql.sql
	mv ./ingresseCLI.jar ./java/ingresseCLI.jar
}






