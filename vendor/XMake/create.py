import os
import templates as tmp
from shutil import copyfile

eclipse_files = ['.classpath', '.project', '.settings/org.eclipse.jdt.core.prefs']

TEMPLATE_LOCATION = 'vendor/templates'
CONFIG_LOCATION = TEMPLATE_LOCATION + '/xahla-config'
SHADER_LOCATION = TEMPLATE_LOCATION + '/shaders'

WORKSPACE_FOLDER = 'workspace'

# Generate all files for an eclipse project
def generateEclipseFiles(programName):
    for f in eclipse_files:
        newFile = open(WORKSPACE_FOLDER + '/' + programName + "/" + f, 'w')

        template = tmp.getTemplate(f, '', 'eclipse/')
        content = tmp.compileBlade(template.read(), {'author':tmp.author, 'programName':programName})

        newFile.write(content)
        print("Created file: {}".format(f))
        
# List of directory for Xahla Core Project
xahla_dir = ['.settings', 
    'src/org/xahla/main',
    'bin/org/xahla/main',
    'config',
    'lib',
    'res/shaders/std']

# Generate a new Xahla Core Project.
def createXahla(programName, perspective=False):
    # Directories
    for dir in xahla_dir:
        path = WORKSPACE_FOLDER + '/' + programName + "/" + dir
        try:
            os.makedirs(path)
        except OSError:
            print("Could not create directory: {}".format(dir))
        else:
            print("Created directory: {}".format(dir))

    # Module Info
    moduleTemplate = tmp.getTemplate("moduleXahla")
    moduleContent = tmp.compileBlade(moduleTemplate.read(), {'programName':programName})
    tmp.writeFile(WORKSPACE_FOLDER + '/' + programName + '/src', 'module-info', moduleContent)

    # Configurations
    copyfile(
        CONFIG_LOCATION + "/rendering.json", 
        WORKSPACE_FOLDER + '/' + programName + "/config/rendering.json"
    )
    print("Created file: config/rendering.json")
    
    if (perspective):
        copyfile(
            CONFIG_LOCATION + "/perspective.json", 
            WORKSPACE_FOLDER + '/' + programName + "/config/perspective.json"
        )
        print("Created file: config/perspective.json")
    else:
        copyfile(
            CONFIG_LOCATION + "/orthographic.json", 
            WORKSPACE_FOLDER + '/' + programName + "/config/orthographic.json"
        )
        print("Created file: config/orthographic.json")

    # Shaders
    
    copyfile(
        SHADER_LOCATION + "/world.vsh",
        WORKSPACE_FOLDER + '/' + programName + "/res/shaders/std/world.vsh"
    )

    copyfile(
        SHADER_LOCATION + "/world.fsh",
        WORKSPACE_FOLDER + '/' + programName + "/res/shaders/std/world.fsh"
    )

    print("Created files: /res/shaders/std/world.vsh and /res/shaders/std/world.fsh")

    # Eclipse Files
    generateEclipseFiles(programName)

    # Main File
    tmp.makeMainXahla(programName)
