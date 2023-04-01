const gulp = require('gulp');
const inlinesource = require('gulp-inline-source');
const replace = require('gulp-replace');
const minify = require('gulp-minify');

gulp.task('minify-js', function() {
  return gulp.src('./target/scala-3.2.2/*-opt/*.js')
   .pipe(minify())
    .pipe(gulp.dest('./target/gulp'));
});

gulp.task('build-single-html', function() {
  return gulp
    .src('./index.html')
    .pipe(replace('.js"></script>', '.js" inline></script>'))
    .pipe(replace('rel="stylesheet">', 'rel="stylesheet" inline>'))
    .pipe(
      inlinesource({
        compress: false,
        ignore: ['png'],
      })
    )
    .pipe(gulp.dest('./target/html'));
});

gulp.task('default', gulp.series('minify-js', 'build-single-html'));
