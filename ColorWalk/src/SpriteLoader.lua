local SpriteLoader = {tag="spriteLoader"}
local graphics = require("graphics")
local options =
{
    frames =
    {
        {   -- frame 1 - Letter blocks
            x = 25,
            y = 33,
            width = 154,
            height = 124
        },
        {   -- frame 2 - Red truck
            x = 206,
            y = 43,
            width = 117,
            height = 60
        },
        {   -- frame 3 - Green helicopter
            x = 333,
            y = 42,
            width = 138,
            height = 68
        },
        {   -- frame 4 - Red bird
            x = 481,
            y = 44,
            width = 77,
            height = 61
        },
        {   -- frame 5 - toybox
            x = 642,
            y = 0,
            width = 364,
            height = 177
        },
        {   -- frame 6 - mom
            x = 12,
            y = 176,
            width = 228,
            height = 760
        },
        {   -- frame 7 daniel
            x = 252,
            y = 350,
            width = 396,
            height = 578
        } 


    }
}
local sheet1 = graphics.newImageSheet( "images/touchables.png", options )

function SpriteLoader:new (o)
	o = o or {};
	setmetatable(o, self);
	self.__index = self;
	return o;
end

function SpriteLoader:getSprite(id)
	if(id == "p1 letter blocks") then
		return display.newImage(sheet1, 1);
	elseif(id == "p1 red truck") then
		return display.newImage(sheet1, 2);
	elseif(id == "p1 green helicopter") then
		return display.newImage(sheet1, 3);
	elseif(id == "p1 red bird") then
		return display.newImage(sheet1, 4);
	elseif(id == "p1 toy box") then
		return display.newImage(sheet1, 5);
	elseif(id == "mom") then
		return display.newImage(sheet1, 6);
	elseif(id == "daniel") then
		return display.newImage(sheet1, 7);
	end
end




return SpriteLoader;