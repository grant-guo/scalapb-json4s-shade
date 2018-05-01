# Shade scalapb-json4s library

scalapb-json4s version 0.3.3 uses higher version json4s library(3.5.1) than spark 2.3.0, and the two json4s versions are not compatible.

This repo shades both json4s and fastxml-jackson

This repo also clears the dependencies of the shaded jar, refer to [here](https://github.com/sbt/sbt-assembly#q-despite-the-concerned-friends-i-still-want-publish-fat-jars-what-advice-do-you-have)