Pod::Spec.new do |spec|
    spec.name                     = 'data'
    spec.version                  = '1.0'
    spec.homepage                 = 'https://github.com/jzbrooks/knapsack'
    spec.source                   = { :http=> ''}
    spec.authors                  = ''
    spec.license                  = ''
    spec.summary                  = 'Wallabag data management library'
    spec.vendored_frameworks      = 'build/cocoapods/framework/data.framework'
    spec.libraries                = 'c++'
    spec.ios.deployment_target = '17'
    spec.osx.deployment_target = '13.0'
    spec.dependency 'HTMLReader'

    spec.pod_target_xcconfig = {
        'KOTLIN_PROJECT_PATH' => ':data',
        'PRODUCT_MODULE_NAME' => 'data',
    }

    spec.script_phases = [
        {
            :name => 'Build data',
            :execution_position => :before_compile,
            :shell_path => '/bin/sh',
            :script => <<-SCRIPT
                if [ "YES" = "$OVERRIDE_KOTLIN_BUILD_IDE_SUPPORTED" ]; then
                  echo "Skipping Gradle build task invocation due to OVERRIDE_KOTLIN_BUILD_IDE_SUPPORTED environment variable set to \"YES\""
                  exit 0
                fi
                set -ev
                REPO_ROOT="$PODS_TARGET_SRCROOT"
                "$REPO_ROOT/../gradlew" -p "$REPO_ROOT" $KOTLIN_PROJECT_PATH:syncFramework \
                    -Pkotlin.native.cocoapods.platform=$PLATFORM_NAME \
                    -Pkotlin.native.cocoapods.archs="$ARCHS" \
                    -Pkotlin.native.cocoapods.configuration="$CONFIGURATION"
            SCRIPT
        }
    ]

end
