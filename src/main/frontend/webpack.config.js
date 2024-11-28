const path = require('path');

module.exports = {
  // ... 기존 설정들 ...
  resolve: {
    extensions: ['.tsx', '.ts', '.js'],
    alias: {
      '@': path.resolve(__dirname, 'src'),
    },
  },
  // ... 나머지 설정들 ...
};