import os
import templates as tmp
from shutil import copyfile

eclipse_files = ['.classpath', '.project', '.settings/org.eclipse.jdt.core.prefs']
TEMPLATE_LOCATION = 'templates'

# Generate all files for an eclipse project
def generateEclipseFiles(programName):
    for f in eclipse_files:
        newFile = open(programName + "/" + f, 'w')

        template = tmp.getTemplate(f, '', 'eclipse/')
        content = tmp.compileBlade(template.read(), {'author':tmp.author, 'programName':programName})

        newFile.write(content)
        print("Created file: {}".format(f))
        
# List of directory for Xahla Core Project
xahla_dir = ['.settings', 
    'src/org/xahla/main',
    'bin/org/xahla/main',
    'config',
    'lib']

# Generate a new Xahla Core Project.
def createXahla(programName, perspective=False):
    # Directories
    for dir in xahla_dir:
        path = programName + "/" + dir
        try:
            os.makedirs(path)
        except OSError:
            print("Could not create directory: {}".format(dir))
        else:
            print("Created directory: {}".format(dir))

    # Module Info
    moduleTemplate = tmp.getTemplate("moduleXahla")
    moduleContent = tmp.compileBlade(moduleTemplate.read(), {'programName':programName})
    tmp.writeFile(programName + '/src', 'module-info', moduleContent)

    # Configurations
    copyfile(TEMPLATE_LOCATION + "/xahla-config/rendering.json", programName + "/config/rendering.json")
    
    if (perspective):
        copyfile(TEMPLATE_LOCATION + "/xahla-config/perspective.json", programName + "/config/perspective.json")
    else:
        copyfile(TEMPLATE_LOCATION + "/xahla-config/orthographic.json", programName + "/config/orthographic.json")

    # Eclipse Files
    generateEclipseFiles(programName)

    # Main File
    tmp.makeMainXahla(programName)
