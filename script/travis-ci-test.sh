#!/bin/bash

mvn test
EXIT="$?"

script/publish_approval_images.sh

exit $EXIT
