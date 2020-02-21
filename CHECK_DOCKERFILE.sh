#!/bin/bash
# uncomment to debug the script
# set -x
# copy the script below into your app code repo (e.g. ./scripts/check_dockerfile.sh) and 'source' it from your pipeline job
#    source ./scripts/check_prebuild.sh
# alternatively, you can source it from online script:
#    source <(curl -sSL "https://raw.githubusercontent.com/open-toolchain/commons/master/scripts/check_dockerfile.sh")
# ------------------
# source: https://raw.githubusercontent.com/open-toolchain/commons/master/scripts/check_dockerfile.sh
ls 

# This script lints Dockerfile.
source <(curl -sSL "https://raw.githubusercontent.com/open-toolchain/commons/master/scripts/check_dockerfile.sh")