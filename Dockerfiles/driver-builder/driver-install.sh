#!/bin/bash

{ \
	echo '#!/bin/bash'; \
	echo 'exec ${IGNIS_HOME}/backend/jre/bin/java -cp "${IGNIS_HOME}/lib/java/*" org.ignis.backend.Main "$@"'; \
} > ${IGNIS_HOME}/bin/ignis-backend
chmod +x ${IGNIS_HOME}/bin/ignis-backend

{ \
	echo '#!/bin/bash'; \
	echo 'if [ -z "$1" ]; then'
    echo '  echo "Insert the job directory to restore"'
    echo '  exit 1'
	echo 'fi'
	echo 'images_dir=$1'
	echo 'sockets_file="$images_dir/sockets.txt"'
	echo 'command=" criu restore -v4 --images-dir $images_dir --shell-job --tcp-established -x"'
	echo 'for socket in $(cat "$sockets_file"); do'
	echo '   command+=" $socket"'
	echo 'done'
	echo '$command'
} > ${IGNIS_HOME}/bin/ignis-restore
chmod +x ${IGNIS_HOME}/bin/ignis-restore

export DEBIAN_FRONTEND=noninteractive
apt update
apt -y --no-install-recommends install python3.8 python3.8-distutils python3-numpy
apt -y install libprotobuf-dev libprotobuf-c-dev protobuf-c-compiler protobuf-compiler python3-protobuf
apt -y install libbsd-dev pkg-config libgnutls28-dev libnftables-dev asciidoc git build-essential libnet-dev libnl-3-dev libcap-dev
wget https://download.openvz.org/criu/criu-3.15.tar.bz2
tar -xjf criu-3.15.tar.bz2
cd criu-3.15
make install
cd ..
apt -y install iptables
apt -y install lsof
