const path = require("path");
const fs = require("fs");

exports.preCommit = ({ version }) => {
  const file = path.resolve(__dirname, "..", "gradle.properties");
  const contents = fs.readFileSync(file);
  
  contents.replace(/version\=([^\n]+)/gi, `version=${version}`);
  
  fs.writeFileSync(file, contents);
};
