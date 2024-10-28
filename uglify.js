const fs = require('fs');
const path = require('path');
const UglifyJS = require('uglify-js');

const jsDirectory = path.join(__dirname, 'src', 'main', 'resources', 'static', 'js');

// Uglify 옵션 설정
const uglifyOptions = {
    compress: {
        dead_code: true,
        drop_debugger: true,
        global_defs: {
            DEBUG: false
        }
    },
    mangle: {
        keep_fnames: true  // 함수명 보존
    },
    output: {
        ascii_only: true,
        comments: false
    },
    sourceMap: false,
    ie8: false
};

fs.readdir(jsDirectory, (err, files) => {
    if (err) {
        console.error('Error reading directory:', err);
        process.exit(1);
    }

    files.forEach(file => {
        if (path.extname(file) === '.js' && !file.endsWith('.min.js')) {
            const filePath = path.join(jsDirectory, file);
            const outputPath = path.join(jsDirectory, `${path.basename(file, '.js')}.min.js`);

            const code = fs.readFileSync(filePath, 'utf8');

            // JSTL 태그와 EL 표현식을 임시 플레이스홀더로 대체
            const jstlPlaceholders = [];
            let processedCode = code.replace(/(<c:.*?>|<\/c:.*?>|\$\{.*?\})/g, (match) => {
                const placeholder = `__JSTL_PLACEHOLDER_${jstlPlaceholders.length}__`;
                jstlPlaceholders.push(match);
                return placeholder;
            });

            try {
                // JavaScript 난독화
                const result = UglifyJS.minify(processedCode, uglifyOptions);

                if (result.error) {
                    console.error(`Error minifying ${file}:`, result.error);
                } else {
                    // JSTL 플레이스홀더를 원래 태그로 복원
                    let finalCode = result.code;
                    jstlPlaceholders.forEach((tag, index) => {
                        finalCode = finalCode.replace(`__JSTL_PLACEHOLDER_${index}__`, tag);
                    });

                    fs.writeFileSync(outputPath, finalCode);
                    console.log(`Successfully minified ${file} -> ${path.basename(outputPath)}`);
                }
            } catch (error) {
                console.error(`Error processing ${file}:`, error);
            }
        }
    });
});