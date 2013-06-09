#!/bin/bash

mvn test -PskipSwtTests
EXIT="$?"

script/publish_approval_images.sh

exit $EXIT
