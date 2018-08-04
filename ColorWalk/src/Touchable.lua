local Touchable = {tag="touchable"}
local SpriteLoader = require("src.SpriteLoader")
local graphics = require("graphics")
local audio = require("audio")
function Touchable:new (o)
	o = o or {};
	setmetatable(o, self);
	self.__index = self;
	return o;
end

function Touchable:createTouchable(touchableData)
	--local image = SpriteLoader:getSprite(spriteName);
	local options = {
		frames = {
			{
				x = touchableData.imageX,
				y = touchableData.imageY,
				width = touchableData.width,
				height =touchableData.height,
			}
		}
	}

	local sheet = graphics.newImageSheet(touchableData.imagePath, options)
	local image = display.newImage(sheet, 1);
	--Scale to correct screen size
	local scaleRatioX = (display.actualContentWidth / 1920);
	local scaleRatioY = (display.actualContentHeight / 1280);
	image:scale(touchableData.scaleX * scaleRatioX, touchableData.scaleY * scaleRatioY);
	image.anchorX = 0.5;
	image.anchorY = 0.5;
	local xDelta = display.actualContentWidth - display.contentWidth;
	local yDelta = display.actualContentHeight - display.contentHeight;
	image.x = ((touchableData.screenX + touchableData.width/2) * scaleRatioX ) - xDelta/2;
	image.y = ((touchableData.screenY + touchableData.height/2) * scaleRatioY ) -yDelta/2;
	image.rotation = touchableData.rotation;

	if(touchableData.soundName ~= nil and touchableData.soundName ~= "none") then
		local sound = audio.loadSound(touchableData.soundPath);
		local function playSound(event)
			audio.setVolume(.1, {channel = 2});
			audio.play(sound, {channel = 2});
		end
		image:addEventListener("tap", playSound, 1);
	end
	return image;
end



return Touchable;