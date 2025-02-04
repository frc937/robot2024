import os

replacements = """
  com.revrobotics.CANSparkMax -> com.revrobotics.spark.SparkMax
  CANSparkMax -> SparkMax
  com.revrobotics.CANSparkLowLevel -> com.revrobotics.spark.SparkLowLevel
  CANSparkMax -> SparkLowLevel
"""

real_replacements = []

for line in replacements.splitlines():
    parts = line.split("->")
    if len(parts) > 1:
        to_replace = parts[0].strip()
        replace_with = parts[1].strip()
        real_replacements.append((to_replace, replace_with))

print(real_replacements)

for (dirpath, dirnames, filenames) in os.walk("."):
    for file in filenames:
        if file == "find_and_replace.py":
            print("Skipped replacement script")
            continue
        file = f"{dirpath}/{file}"
        
        try:
            data = open(file, "r").read()

            for replacement in real_replacements:
                data = data.replace(replacement[0], replacement[1])
            
            open(file, "w").write(data)
                
        except Exception as e:
            print(e)
                
 

