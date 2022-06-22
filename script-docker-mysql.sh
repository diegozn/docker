#!/bin/bash
#
# "------------------------------------------------------------------------"
# Cluster Docker   
# Criador: Diego Silva"
# "------------------------------------------------------------------------"
#
# "Verificações:"
# "   1 - O script 'script-docker-mysql.sh' está na mesma pasta que o script.sql"
# "   2 - O terminal bash deve estar aberto na pasta onde está o script.sql e o script-docker-mysql.sh"
# "   3 - Só pode existir 1 arquivo.sql dentro da pasta!"
# "   4 - O arquivo sql não deve ter nenhum 'CREATE DATABASE' pois será criado neste shell script."
# "   5 - O aqruivo sql não pode ter nenhum 'USE <database>' pois o script-docker-mysql.sh também já faz essa função..."
# "   obs: O arquivo sql deve ter somente do primeiro 'CREATE TABLE...' pra baixo..."

# OBS: SÓ NÃO ESQUEÇA DE DEFINIR TODAS AS VARIAVEIS DE AMBIENTE E DEFINIR O FILE.SQL NO FINAL DO script-docker-mysql.sh.

clear
echo "Criando Container Docker MySQL"
read -p "Press Enter to continue ..."

# VARIAVEIS DO CONTAINER DE DOCKER MYSQL.
# PODE TROCAR TODAS AS VARIÁVEIS DE AMBIENTE.
# TROCA A PORTA PRA NÃO FICAR IGUAL A PADRÃO DO MYSQL... PODE COLOCAR QUALQUER COISA.. TEM QUE SER 4 DIGITOS.
# Obs: a alterações das variaveis exige manter essas aspas duplas, modifique somente o que está dentro das "aqui".

portaBanco="3355"
nomeContainer="contis"
nomeBanco="ingsoft"
senhaRootBanco="root"
userBanco="urubu100"
senhaUserBanco="urubu100"

# COMANDO DOCKER PARA CRIAR CONTAINER.

docker run -d -p $portaBanco:3306 --name $nomeContainer -e "MYSQL_DATABASE=$nomeBanco" -e "MYSQL_ROOT_PASSWORD=$senhaRootBanco" -e "MYSQL_USER=$userBanco" -e "MYSQL_PASSWORD=$senhaUserBanco" mysql

echo "Aguarde 20 Segundos (Inicializando Container...)"
sleep 20
read -p "Press Enter to continue ..."

# INSERINDO DADOS NO MYSQL DO CONTAINER
# ARQUIVO SQL / ABAIXO SÓ COLOCAR O NOME DO ARQUIVO
# OBS: O ARQUIVO DEVERÁ ESTAR NA MESMA PASTA QUE O script-docker-mysql.sh OU SEJA, MESMA PASTA QUE ESTÁ ESSE SCRIPT.

file_sql="script.sql"

# COMANDO DOCKER PARA INSERIR
docker exec -i $nomeContainer sh -c 'exec mysql -uroot -proot '$nomeBanco'' <"$file_sql"

#
# "===  FIM DO SCRIPT 'script-docker-mysql.sh', OBRIGADO. ==="
# "----------------------------------------------------------"
