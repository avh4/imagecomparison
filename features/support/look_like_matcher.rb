require 'RMagick'
require 'java'
require 'digest/sha1'
java_import 'java.awt.image.BufferedImage'
java_import 'javax.imageio.ImageIO'
java_import 'net.avh4.util.imagecomparison.ImageComparison'

module ImageMatchers
  class LookLike
    def initialize(string)
      @base = "src/test/resources/"
      if (string =~ /\.png$/) then
        @png_file = @base + string
      else
        @png_file = @base + (Digest::SHA1.hexdigest string) + ".png"
      end
      @actual_filename = @png_file.gsub(/\.png$/, "-actual.png")
    end
    def matches?(view)
      @object = view
      
      return ImageComparison.matches(view, @png_file, @actual_filename)
      # ImageIO.write(bi, "png", java.io.File.new(@actual_filename));
      
      # expected_file = File.join("approvals", @png_file)
      # if !File.exist?(expected_file) then
      #   @error = "no reference file '#{expected_file}'"
      #   return false
      # end
      # expected = Magick::Image.read(expected_file).first
      # actual = Magick::Image.read(@actual_filename).first
      # 
      # # Check the image size
      # actual_size = [actual.columns, actual.rows]
      # expected_size = [expected.columns, expected.rows]
      # if actual_size != expected_size then
      #   @error = "expected: <#{expected_size.join('x')}>, actual: <#{actual_size.join('x')}>"
      #   return false
      # end
      # 
      # # Check the image data
      # diff_image, distortion = expected.compare_channel(actual, Magick::MeanSquaredErrorMetric)
      # if distortion > 0 then
      #   diff_file = @png_file.gsub(/\.png$/, "-diff.png")
      #   diff_image.write(diff_file)
      #   @error = "distortion = #{distortion}"
      #   return false
      # end
      # 
      # # If we make it here, the images match
      # return true
    end
    def description
      "look like"
    end
    def failure_message
      "expected '#{@actual_filename}' to look like '#{@png_file}': #{@error}"
    end
    def negative_failure_message
      "expected '#{@actual_filename}' not to look like '#{@png_file}'"
    end
  end

  def look_like(description)
    LookLike.new(description)
  end
end

World(
  ImageMatchers
  )