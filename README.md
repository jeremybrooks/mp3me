# mp3me
Utility to convert audio files to mp3. Uses ffmpeg to do the heavy lifting.

To use mp3me, you must have ffmpeg installed. By default, mp3me will look for ffmpeg at /usr/local/bin/ffmpeg. If you have it installed in a different location, open the Preferences dialog and set the correct location.

Coding: Jeremy Brooks

### Libraries
* Log4j (https://logging.apache.org/log4j/2.x/)
* pressplay (https://github.com/jeremybrooks/pressplay)

Installer built with Install4j (https://www.ej-technologies.com/products/install4j/overview.html)

# Development Notes
Install4j is used during the Maven package goal to build the installer. If you want to skip the Install4j step, use the install4j.skip property:

```mvn -Dinstall4j.skip clean package```

The generated macOS bundle will be signed and notarized. This is a time-consuming process, so during development you may want to disable signing. To do this, use the disableSigning property:

```mvn -Dinstall4j.disableSigning clean package```

When you are ready to build a release version:

1. Update the version in pom.xml
2. Build the application: `mvn clean package`
3. If the build is successful:
    1. Commit and push
    2. Tag the repo `git tag -a x.y.z`
    3. Push the tag `git push origin --tags`
    4. Update the version in pom.xml for the next snapshot
    5. Commit and push
