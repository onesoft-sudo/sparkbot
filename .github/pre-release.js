const path = require("path");
const fs = require("fs");

exports.preCommit = ({ version }) => {
  const file = path.resolve(__dirname, "..", "gradle.properties");
  const contents = fs.readFileSync(file).toString();
  
  contents.replace(/version\=([^\n]+)/gi, `version=${version}`);
  
  fs.writeFileSync(file, contents);

  const { exec } = require("child_process");

  exec("git add --all", (error, stdout, stderr) => {
      if (error) {
          console.error(`error: ${error.message}`);
          return;
      }

      if (stderr) {
          console.error(`stderr: ${stderr}`);
          return;
      }
      
      console.log(`stdout: ${stdout}`);
  });
};
