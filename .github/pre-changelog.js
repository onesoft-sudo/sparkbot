const path = require("path");
const fs = require("fs");

const file = path.resolve(__dirname, "..", "gradle.properties");
const contents = fs.readFileSync(file).toString();
const version = contents.match(/version\=([^\n]+)/gi)[0].split('=')[1];

exports.preVersionGeneration = () => {
    fs.writeFileSync(path.join(__dirname, "..", ".version.json"), `{"version":"${version}"}`);
    return version;
}

exports.preTagGeneration = (tag) => {
    return `v${version}`;
}